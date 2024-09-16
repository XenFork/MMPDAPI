import cn.hutool.core.comparator.VersionComparator
import org.dom4j.io.SAXReader


fun applyFindMavenVersion(url: String, versionSelect: String): String {//https://maven.fabricmc.net/net/fabricmc/fabric-loader/maven-metadata.xml
    val saxReader = SAXReader()
    val read = saxReader.read(url)
    val rootElement = read.rootElement
    val versions = rootElement.element("versioning").element("versions").elements("version")
    var versionTarget = ""
    for (version in versions) {
        if (version.textTrim.isEmpty().not() && version.text.contains(versionSelect)) {
            if (VersionComparator.INSTANCE.compare(version.text, versionTarget) > 0) {
                versionTarget = version.text
            }
        }
    }
    return versionTarget
}