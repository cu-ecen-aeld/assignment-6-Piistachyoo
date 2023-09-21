
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-Piistachyoo.git;protocol=https;branch=master \
           file://S96misc_driver \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "df2a1ba3cf470b86c7bfcbc7ad9411d35bd505ff"

S = "${WORKDIR}/git/misc-modules"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE  += "KERNELDIR=${STAGING_KERNEL_DIR}"
RPROVIDES:${PN} += " kernel-module-faulty"
RPROVIDES:${PN} += " kernel-module-hello"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S96misc_driver"
inherit update-rc.d

FILES:${PN} += "${INIT_D_DIR}/${INITSCRIPT_NAME:${PN}}"

FILES:${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/extra/module_load"
FILES:${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/extra/module_unload"

do_install () {

	install -d ${D}${INIT_D_DIR}
    install -m 0755 ${WORKDIR}/${INITSCRIPT_NAME:${PN}} ${D}${INIT_D_DIR}
	
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
    install -m 755 ${S}/faulty.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/faulty.ko
    install -m 755 ${S}/hello.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/hello.ko
	install -m 755 ${S}/module_load ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/module_load
	install -m 755 ${S}/module_unload ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/module_unload
}
