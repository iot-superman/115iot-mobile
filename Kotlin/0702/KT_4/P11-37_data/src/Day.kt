/**
 * 補充使用的 enum：DayName（避免與程式中其他 `Day` 宣告產生衝突）
 *
 * 這個檔案是我先前新增的輔助 enum，為避免和 `Dataclass.kt` 中的 `Day`
 * 發生命名衝突，將其命名為 `DayName`。此 enum 非專案必需，可視情況使用或刪除。
 */
enum class DayName(val chinese: String) {
	/** 星期一 */
	MONDAY(chinese = "星期一"),
	/** 星期二 */
	TUESDAY(chinese = "星期二"),
	/** 星期三 */
	WEDNESDAY(chinese = "星期三"),
	/** 星期四 */
	THURSDAY(chinese = "星期四"),
	/** 星期五 */
	FRIDAY(chinese = "星期五"),
	/** 星期六 */
	SATURDAY(chinese = "星期六"),
	/** 星期日 */
	SUNDAY(chinese = "星期日");

	override fun toString(): String = "${name} (${chinese})"
}


