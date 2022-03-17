import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class SearchController : SearchControllerContractor {

    companion object {
        private const val EMPTY = ""
        private const val DIR = "user.dir"
        private const val TIME_PATTERN = "dd-M-yyyy hh:mm:ss"
    }

    override fun findRequestedDirectory(directoryName: String): String {
        val primaryDir = File(System.getProperty(DIR))

        val simpleDateFormat = SimpleDateFormat(TIME_PATTERN, Locale.getDefault())
        return primaryDir.walkTopDown()
                .filter { it.name.startsWith(directoryName) }
                .sortedBy { it.length() }
                .map { mapPrimaryFile(it, simpleDateFormat) }
                .filter { it.isNotEmpty() }
                .joinToString(separator = EMPTY)
    }

    private fun mapPrimaryFile(primaryFile: File, simpleDateFormat: SimpleDateFormat): String {
        return primaryFile.listFiles()?.let { arrayOfInnerFiles ->
            val content = arrayOfInnerFiles
                    .sortedBy { file -> file.length() }
                    .joinToString(separator = EMPTY) { file ->
                        getFileContent(file, simpleDateFormat)
                    }
            val countFiles = primaryFile.listFiles()?.filter { it.isFile && !it.isHidden }?.size
            val totalFolderSize = arrayOfInnerFiles.map { it.length() }.sum()
            "amount of files in $primaryFile folder: $countFiles\n" +
                    "total $primaryFile size is $totalFolderSize bytes\n" +
                    "$content\n"
        } ?: EMPTY
    }

    private fun getFileContent(file: File, simpleDateFormat: SimpleDateFormat): String {
        return "path = ${file.absolutePath}, " +
                "size ${file.length()} bytes, " +
                "${simpleDateFormat.format(Date(file.lastModified()))} last modify time\n"
    }
}
