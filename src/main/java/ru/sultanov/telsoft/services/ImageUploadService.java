package ru.sultanov.telsoft.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.sultanov.telsoft.entity.ImageModel;
import ru.sultanov.telsoft.entity.Person;
import ru.sultanov.telsoft.repository.ImageRepository;
import ru.sultanov.telsoft.repository.UserRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ImageUploadService {
    public static final Logger LOG = LoggerFactory.getLogger(ImageUploadService.class);

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    @Autowired
    public ImageUploadService(ImageRepository imageRepository, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }

    public ImageModel uploadImageToUser(MultipartFile file, Principal principal) throws IOException {

        Person person = getPersonByPrincipal(principal);
        LOG.info("Uploading image profile to User", person.getUsername());

        ImageModel userProfileImage = imageRepository.findByPerson(person)
                .orElse(null);

        if (!ObjectUtils.isEmpty(userProfileImage)) {
            imageRepository.delete(userProfileImage);
        }

        ImageModel imageModel = new ImageModel();
        imageModel.setPerson(person);
        imageModel.setImageBytes(compressBytes(file.getBytes()));
        return imageRepository.save(imageModel);
    }


    public ImageModel getImageToUser(Principal principal) {
        Person person = getPersonByPrincipal(principal);

        ImageModel imageModel = imageRepository.findByPerson(person)
                .orElse(null);

        if (!ObjectUtils.isEmpty(imageModel)) {
            imageModel.setImageBytes(decompressBytes(imageModel.getImageBytes()));
        }
        return imageModel;
    }


    private byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            LOG.error("Cannot compress Bytes");
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    private static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException e) {
            LOG.error("Cannot decompress Bytes");
        }
        return outputStream.toByteArray();
    }


    //Разобраться что за хрень. Типа возвращаем 1 пост из коллектора
    private <T> Collector<T, ?, T> toSinglePostCollector() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }

    private Person getPersonByPrincipal(Principal principal) {
        return userRepository.findPersonByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User " + principal.getName() + " not found"));
    }
}
