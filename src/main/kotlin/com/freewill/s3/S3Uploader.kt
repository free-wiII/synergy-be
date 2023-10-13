package com.freewill.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.AmazonS3Exception
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.freewill.s3.dto.S3UploadRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@Component
class S3Uploader(
    private val amazonS3Client: AmazonS3
) {
    @Value("\${cloud.aws.s3-bucket}")
    lateinit var s3Bucket: String

    @Throws(IOException::class)
    fun upload(request: S3UploadRequest): String {
        val fileName: String = createFileName(request.userId)

        putS3(request.image, fileName)
        return getS3Url(fileName)
    }

//    fun deleteFiles(imageDeleteRequest: ImageDeleteRequest) {
//        val fileNames: List<String> = imageDeleteRequest.getFileNamesWithPath()
//        fileNames.forEach(Consumer { fileName: String ->
//            deleteFromS3(
//                fileName
//            )
//        })
//    }

//    private fun deleteFromS3(fileName: String) {
//        validateFromS3(fileName)
//        amazonS3Client.deleteObject(DeleteObjectRequest(s3Bucket, fileName))
//    }

    private fun validateFromS3(fileName: String) {
        if (!amazonS3Client.doesObjectExist(s3Bucket, fileName)) {
            throw AmazonS3Exception("Object $fileName does not exist")
        }
    }

    @Throws(IOException::class)
    private fun putS3(multipartFile: MultipartFile, fileName: String, objectMetadata: ObjectMetadata) {
        amazonS3Client.putObject(
            PutObjectRequest(s3Bucket, fileName, multipartFile.inputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        )
    }

    @Throws(IOException::class)
    private fun putS3(image: MultipartFile, fileName: String): String {
        amazonS3Client.putObject(
            PutObjectRequest(s3Bucket, fileName, image.inputStream, getObjectMetadata(image))
                .withCannedAcl(CannedAccessControlList.PublicRead)
        )
        return getS3Url(fileName)
    }

    private fun getS3Url(fileName: String): String {
        return amazonS3Client.getUrl(s3Bucket, fileName).toString()
    }

    private fun createFileName(userId: Long): String {
        return "profiles/$userId"
    }

    private fun getObjectMetadata(multipartFile: MultipartFile): ObjectMetadata {
        val objectMetaData = ObjectMetadata()
        objectMetaData.contentType = multipartFile.contentType
        objectMetaData.contentLength = multipartFile.size
        return objectMetaData
    }
}
