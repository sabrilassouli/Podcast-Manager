package com.example.podcastmanager.Controller

import com.example.podcastmanager.Models.Episode
import com.example.podcastmanager.Services.EpisodeService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Paths


@RestController
@RequestMapping("/fileupload")
@CrossOrigin(origins = ["http://localhost:5173"], allowedHeaders = ["*"])

class FileUploadController {
    @Autowired
    lateinit var service : EpisodeService

    @PostMapping("/files")
    fun uploadFile(@RequestParam(value = "file") file: MultipartFile, request: HttpServletRequest): ResponseEntity<*> {
        val contentType = request.contentType
        println("uploadfile was reached")
        return try {
            // Make sure the file is not empty
            if (file.isEmpty) {
                return ResponseEntity.badRequest().body("File is empty")
            }

            // Save the file to the specified folder
            val uploadFolder = "vue-project/uploadedfiles"
            val fileName = file.originalFilename
            println(fileName)
            file.transferTo(Paths.get(uploadFolder, fileName))

            // Return a success response if the file is saved successfully
            ResponseEntity.ok().body("File uploaded successfully")
        } catch (e: IOException) {
            // Handle any exception that occurs during file upload
            e.printStackTrace()
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed")
        }
    }
}