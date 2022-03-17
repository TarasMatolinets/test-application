import java.util.Scanner

fun main() {
    print("Type directory for getting the result!!!\n")

    val input = Scanner(System.`in`)

    if (input.hasNext()) {
        findResult(input.nextLine())
    } else {
        println("Input was not found")
    }
}

private fun findResult(requestedDir: String) {
    val searchController: SearchControllerContractor = SearchController()
    val result = searchController.findRequestedDirectory(requestedDir)

    if (result.isEmpty()) {
        println("Folder was not found")
    } else {
        println(result)
    }
}
