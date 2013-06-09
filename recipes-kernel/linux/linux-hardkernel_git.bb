DESCRIPTION = "Linux kernel for the Hardkernel ODROID-U2 device"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

# Mark archs/machines that this kernel supports
COMPATIBLE_MACHINE = "odroid-u2"

inherit kernel siteinfo

SRC_URI = " \
  git://github.com/hardkernel/linux.git;branch=odroid-3.8.y \
"

S = "${WORKDIR}/git/"

SRCREV = "${AUTOREV}"

KV = "3.8.13"
PV = "${KV}+gitr${SRCPV}"

do_configure_prepend() {
     yes '' | oe_runmake odroidu2_defconfig
}

do_install_append() {
    # Helper script provided by Mauro Ribeiro
    tools/hardkernel/genBscr.sh
    oe_runmake headers_install INSTALL_HDR_PATH=${D}${exec_prefix}/src/linux-${KERNEL_VERSION} ARCH=$ARCH
}

do_deploy_append() {
    cp -v *.scr ${DEPLOYDIR}
}

PACKAGES =+ "kernel-headers"
FILES_kernel-headers = "${exec_prefix}/src/linux*"
