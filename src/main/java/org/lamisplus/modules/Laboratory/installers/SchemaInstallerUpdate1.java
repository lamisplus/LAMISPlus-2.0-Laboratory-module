package org.lamisplus.modules.Laboratory.installers;

import com.foreach.across.core.annotations.Installer;
import com.foreach.across.core.installers.AcrossLiquibaseInstaller;
import org.springframework.core.annotation.Order;

@Order(2)
@Installer(name = "schema-installer-laboratory-update1",
        description = "Update laboratory tables",
        version = 1)
public class SchemaInstallerUpdate1 extends AcrossLiquibaseInstaller {
    public SchemaInstallerUpdate1() {
        super("classpath:installers/laboratory/schema/schema-1.xml");
    }
}
