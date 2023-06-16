package com.c23ps422.reclothes.helper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream
import java.text.DateFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

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

fun formatMoney(amount: Int): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    return format.format(amount)
}


fun formatDate(currentDate: String): String? {
    val currentFormat = "yyyy-MM-dd'T'hh:mm:ss.SSSSSS'Z'"
    val targetFormat = "dd MMM yyyy | HH:mm"
    val timezone = "GMT"
    val currentDf: DateFormat = SimpleDateFormat(currentFormat, Locale.getDefault())
    currentDf.timeZone = TimeZone.getTimeZone(timezone)
    val targetDf: DateFormat = SimpleDateFormat(targetFormat, Locale.getDefault())
    var targetDate: String? = null
    try {
        val date = currentDf.parse(currentDate)
        if (date != null) {
            targetDate = targetDf.format(date)
        }
    } catch (ex: ParseException) {
        ex.printStackTrace()
    }
    return targetDate
}
