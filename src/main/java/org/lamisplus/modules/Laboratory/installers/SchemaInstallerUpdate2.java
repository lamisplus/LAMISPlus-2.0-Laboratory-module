package org.lamisplus.modules.Laboratory.installers;

import com.foreach.across.core.annotations.Installer;
import com.foreach.across.core.installers.AcrossLiquibaseInstaller;
import org.springframework.core.annotation.Order;

@Order(3)
@Installer(name = "schema-installer-laboratory-update2",
        description = "Fix UUID issues",
        version = 1)
public class SchemaInstallerUpdate2 extends AcrossLiquibaseInstaller {
    public SchemaInstallerUpdate2() {
        super("classpath:installers/laboratory/schema/schema-2.xml");
    }
}
