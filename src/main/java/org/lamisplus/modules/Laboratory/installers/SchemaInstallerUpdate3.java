package org.lamisplus.modules.Laboratory.installers;

import com.foreach.across.core.annotations.Installer;
import com.foreach.across.core.installers.AcrossLiquibaseInstaller;
import org.springframework.core.annotation.Order;

@Order(4)
@Installer(name = "schema-installer-laboratory-update3",
        description = "Add patientId and facilityId to lab tables",
        version = 1)
public class SchemaInstallerUpdate3 extends AcrossLiquibaseInstaller {
    public SchemaInstallerUpdate3() {
        super("classpath:installers/laboratory/schema/schema-3.xml");
    }
}
