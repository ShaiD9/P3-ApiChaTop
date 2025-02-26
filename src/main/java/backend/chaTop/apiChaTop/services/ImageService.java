package backend.chaTop.apiChaTop.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Service pour la gestion des images.
 * Permet de sauvegarder des images avec un nom unique.
 */
@Service
public class ImageService {

    private static final String IMAGE_DIRECTORY = "images/";

    /**
     * Sauvegarde une image avec un nom unique.
     *
     * @param file L'image à sauvegarder sous forme de fichier Multipart.
     * @return Le chemin d'accès de l'image stockée.
     * @throws IOException En cas d'erreur lors de la sauvegarde du fichier.
     */
    public String saveImage(MultipartFile file) throws IOException {
        // Vérifier si le dossier images existe, sinon le créer
        File uploadDir = new File(IMAGE_DIRECTORY);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Extraire l'extension du fichier
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // Générer un nom unique
        String uniqueFileName = UUID.randomUUID().toString() + extension;

        // Sauvegarder l'image
        Path filePath = Path.of(IMAGE_DIRECTORY, uniqueFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return filePath.toString().replace("\\", "/"); // Pour compatibilité Windows/Linux
    }
}
