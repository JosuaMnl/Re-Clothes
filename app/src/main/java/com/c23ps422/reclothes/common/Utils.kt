package com.c23ps422.reclothes.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream

/**
 * Compresses the given image file and returns the compressed file.
 *
 * @param file The image file to be compressed.
 * @return The compressed file, or null if the input file is null.
 */
fun compressImage(file: File?): File? {
    if (file == null) {
        return null
    }

    // Create BitmapFactory.Options object
    val options = BitmapFactory.Options()
    options.inSampleSize =
        2 // Adjust the sample size as needed to control image quality vs. file size

    // Decode the image file into a Bitmap using BitmapFactory.decodeFile
    val bitmap = BitmapFactory.decodeFile(file.path, options)

    // Create a temporary file to store the compressed image
    val compressedFile = File.createTempFile("compressed_image_", ".jpg")

    // Create an output stream to write the compressed image data
    val outputStream = FileOutputStream(compressedFile)

    // Compress the bitmap to JPEG format and write it to the output stream
    bitmap?.compress(
        Bitmap.CompressFormat.JPEG,
        80,
        outputStream
    ) // Adjust the compression quality as needed (0-100)

    // Flush and close the output stream
    outputStream.flush()
    outputStream.close()

    return compressedFile
}


