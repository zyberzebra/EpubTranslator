package zebra.zyber.epubtranslator

import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

fun main() {
    generateTestEpub()
}

fun generateTestEpub() {
    // Create test resources directory if it doesn't exist
    val resourcesDir = File("src/test/resources")
    resourcesDir.mkdirs()
    
    val outputFile = File(resourcesDir, "test.epub")
    
    ZipOutputStream(FileOutputStream(outputFile)).use { zip ->
        // Add mimetype file (must be first and uncompressed)
        zip.setLevel(0) // No compression for mimetype
        zip.putNextEntry(ZipEntry("mimetype"))
        zip.write("application/epub+zip".toByteArray())
        zip.closeEntry()
        
        // Reset compression level for other files
        zip.setLevel(ZipOutputStream.DEFLATED)
        
        // Add container.xml
        zip.putNextEntry(ZipEntry("META-INF/container.xml"))
        zip.write("""<?xml version="1.0" encoding="UTF-8"?>
            |<container version="1.0" xmlns="urn:oasis:names:tc:opendocument:xmlns:container">
            |    <rootfiles>
            |        <rootfile full-path="content.opf" media-type="application/oebps-package+xml"/>
            |    </rootfiles>
            |</container>""".trimMargin().toByteArray())
        zip.closeEntry()
        
        // Add content.opf
        zip.putNextEntry(ZipEntry("content.opf"))
        zip.write("""<?xml version="1.0" encoding="UTF-8"?>
            |<package xmlns="http://www.idpf.org/2007/opf" unique-identifier="uid" version="3.0">
            |    <metadata xmlns:dc="http://purl.org/dc/elements/1.1/">
            |        <dc:title>Test EPUB</dc:title>
            |        <dc:language>en</dc:language>
            |        <dc:identifier id="uid">test-epub-${System.currentTimeMillis()}</dc:identifier>
            |        <dc:creator>Test Generator</dc:creator>
            |    </metadata>
            |    <manifest>
            |        <item id="chapter1" href="chapter1.xhtml" media-type="application/xhtml+xml"/>
            |        <item id="chapter2" href="chapter2.xhtml" media-type="application/xhtml+xml"/>
            |    </manifest>
            |    <spine>
            |        <itemref idref="chapter1"/>
            |        <itemref idref="chapter2"/>
            |    </spine>
            |</package>""".trimMargin().toByteArray())
        zip.closeEntry()
        
        // Add chapter 1
        zip.putNextEntry(ZipEntry("chapter1.xhtml"))
        zip.write("""<?xml version="1.0" encoding="UTF-8"?>
            |<!DOCTYPE html>
            |<html xmlns="http://www.w3.org/1999/xhtml" xmlns:epub="http://www.idpf.org/2007/ops">
            |<head>
            |    <title>Chapter 1</title>
            |</head>
            |<body>
            |    <h1>Chapter 1: Introduction</h1>
            |    <p>This is a test EPUB file generated for testing the EpubTranslator application.</p>
            |    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor 
            |    incididunt ut labore et dolore magna aliqua.</p>
            |</body>
            |</html>""".trimMargin().toByteArray())
        zip.closeEntry()
        
        // Add chapter 2
        zip.putNextEntry(ZipEntry("chapter2.xhtml"))
        zip.write("""<?xml version="1.0" encoding="UTF-8"?>
            |<!DOCTYPE html>
            |<html xmlns="http://www.w3.org/1999/xhtml" xmlns:epub="http://www.idpf.org/2007/ops">
            |<head>
            |    <title>Chapter 2</title>
            |</head>
            |<body>
            |    <h1>Chapter 2: More Content</h1>
            |    <p>This chapter contains additional test content with different structures.</p>
            |    <ul>
            |        <li>Test item 1</li>
            |        <li>Test item 2</li>
            |        <li>Test item 3</li>
            |    </ul>
            |    <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi 
            |    ut aliquip ex ea commodo consequat.</p>
            |</body>
            |</html>""".trimMargin().toByteArray())
        zip.closeEntry()
    }
    
    println("Test EPUB generated at: ${outputFile.absolutePath}")
} 