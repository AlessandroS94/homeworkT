package it.telespazio.work.business.impl;

import it.telespazio.work.business.interfaces.FileElaborationInterface;
import it.telespazio.work.model.ContentFile;
import it.telespazio.work.model.File;
import it.telespazio.work.repository.ContentRepository;
import it.telespazio.work.repository.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.HexFormat;

import java.io.IOException;


@Service
public class FileElaborationImpl implements FileElaborationInterface {

    @Value("${file.division}")
    private Integer partSize;

    private final FileRepository fileRepository;
    private final ContentRepository contentRepository;

    public FileElaborationImpl(FileRepository fileRepository, ContentRepository contentRepository) {
        this.fileRepository = fileRepository;
        this.contentRepository = contentRepository;
    }

    @Override
    public void saveFile(MultipartFile file) throws IOException {
        byte[] allBytes = file.getBytes();

        File fileToSave = new File();
        fileToSave.setName(file.getOriginalFilename());

        for (int i = 0; i < allBytes.length; i += partSize) {
            int length = Math.min(partSize, allBytes.length - i);
            byte[] tmp = new byte[length];
            System.arraycopy(allBytes, i, tmp, 0, length);

            ContentFile contentFile = new ContentFile();
            contentFile.setStartContent((long) i);
            contentFile.setEndContent((long) (i + length));
            contentFile.setLengthContent((long) length);
            contentFile.setContent(tmp);

            fileToSave.addContentFile(contentFile);
        }
        fileRepository.save(fileToSave);
    }


    @Override
    public String getFile(Long id, Long start, Long end) {
        fileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("File not found"));

        long totalLength = end - start;
        if (totalLength <= 0) {
            return "";
        }
        if (totalLength > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Requested range is too large");
        }

        byte[] result = new byte[(int) totalLength];

        var partOfFile = contentRepository
                .findByFileIdAndStartContentLessThanAndEndContentGreaterThanOrderByStartContent(
                        id, end, start
                );

        for (ContentFile contentFile : partOfFile) {
            long partStart = contentFile.getStartContent();
            long partEnd   = contentFile.getEndContent();

            if (partEnd <= start || partStart >= end) {
                continue;
            }

            byte[] part = contentFile.getContent();

            long realStart = Math.max(partStart, start);
            long realStop   = Math.min(partEnd, end);

            int realLength = (int) (realStop - realStart);
            if (realLength <= 0) {
                continue;
            }

            int srcPos = (int) (realStart - partStart);
            int dstPos = (int) (realStart - start);

            System.arraycopy(part, srcPos, result, dstPos, realLength);
        }

        return HexFormat.of().formatHex(result);
    }
}
