package org.sixdouglas.git.environment

/**
 * Colors and names are picked from here: https://htmlcolorcodes.com/color-chart/flat-design-color-chart/
 */
object EnvironmentColor {
    val colors: ArrayList<String> = arrayListOf(
            "#F9EBEA", "#F2D7D5", "#E6B0AA", "#D98880", "#CD6155", "#C0392B", "#A93226", "#922B21", "#7B241C", "#641E16", // POMEGRANATE
            "#FDEDEC", "#FADBD8", "#F5B7B1", "#F1948A", "#EC7063", "#E74C3C", "#CB4335", "#B03A2E", "#943126", "#78281F", // ALIZARIN
            "#F5EEF8", "#EBDEF0", "#D7BDE2", "#C39BD3", "#AF7AC5", "#9B59B6", "#884EA0", "#76448A", "#633974", "#512E5F", // AMETHYST
            "#F4ECF7", "#E8DAEF", "#D2B4DE", "#BB8FCE", "#A569BD", "#8E44AD", "#7D3C98", "#6C3483", "#5B2C6F", "#4A235A", // WISTERIA
            "#EAF2F8", "#D4E6F1", "#A9CCE3", "#7FB3D5", "#5499C7", "#2980B9", "#2471A3", "#1F618D", "#1A5276", "#154360", // BELIZE HOLE
            "#EBF5FB", "#D6EAF8", "#AED6F1", "#85C1E9", "#5DADE2", "#3498DB", "#2E86C1", "#2874A6", "#21618C", "#1B4F72", // PETER RIVER
            "#E8F8F5", "#D1F2EB", "#A3E4D7", "#76D7C4", "#48C9B0", "#1ABC9C", "#17A589", "#148F77", "#117864", "#0E6251", // TURQUOISE
            "#E8F6F3", "#D0ECE7", "#A2D9CE", "#73C6B6", "#45B39D", "#16A085", "#138D75", "#117A65", "#0E6655", "#0B5345", // GREEN SEA
            "#E9F7EF", "#D4EFDF", "#A9DFBF", "#7DCEA0", "#52BE80", "#27AE60", "#229954", "#1E8449", "#196F3D", "#145A32", // NEPHRITIS
            "#EAFAF1", "#D5F5E3", "#ABEBC6", "#82E0AA", "#58D68D", "#2ECC71", "#28B463", "#239B56", "#1D8348", "#186A3B", // EMERALD
            "#FEF9E7", "#FCF3CF", "#F9E79F", "#F7DC6F", "#F4D03F", "#F1C40F", "#D4AC0D", "#B7950B", "#9A7D0A", "#7D6608", // SUNFLOWER
            "#FEF5E7", "#FDEBD0", "#FAD7A0", "#F8C471", "#F5B041", "#F39C12", "#D68910", "#B9770E", "#9C640C", "#7E5109", // ORANGE
            "#FDF2E9", "#FAE5D3", "#F5CBA7", "#F0B27A", "#EB984E", "#E67E22", "#CA6F1E", "#AF601A", "#935116", "#784212", // CARROT
            "#FBEEE6", "#F6DDCC", "#EDBB99", "#E59866", "#DC7633", "#D35400", "#BA4A00", "#A04000", "#873600", "#6E2C00", // PUMPKIN
            "#FDFEFE", "#FBFCFC", "#F7F9F9", "#F4F6F7", "#F0F3F4", "#ECF0F1", "#D0D3D4", "#B3B6B7", "#979A9A", "#7B7D7D", // CLOUDS
            "#F8F9F9", "#F2F3F4", "#E5E7E9", "#D7DBDD", "#CACFD2", "#BDC3C7", "#A6ACAF", "#909497", "#797D7F", "#626567", // SILVER
            "#F4F6F6", "#EAEDED", "#D5DBDB", "#BFC9CA", "#AAB7B8", "#95A5A6", "#839192", "#717D7E", "#5F6A6A", "#4D5656", // CONCRETE
            "#F2F4F4", "#E5E8E8", "#CCD1D1", "#B2BABB", "#99A3A4", "#7F8C8D", "#707B7C", "#616A6B", "#515A5A", "#424949", // ASBESTOS
            "#EBEDEF", "#D6DBDF", "#AEB6BF", "#85929E", "#5D6D7E", "#34495E", "#2E4053", "#283747", "#212F3C", "#1B2631", // WET ASPHALT
            "#EAECEE", "#D5D8DC", "#ABB2B9", "#808B96", "#566573", "#2C3E50", "#273746", "#212F3D", "#1C2833", "#17202A"  // MIDNIGHT BLUE
    )

    fun randomColor(): String {
        return colors.shuffled().first()
    }
}