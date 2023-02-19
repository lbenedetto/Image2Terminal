import org.fusesource.jansi.Ansi

data class State(var background: RGBa?, var foreground: RGBa?) {
    companion object {
        val DEFAULT = State(null, null)
    }

    fun withDefaultBackground(): State {
        return State(null, foreground)
    }

    fun withBackground(color: RGBa): State {
        return State(color, foreground)
    }

    fun withDefaultForeground(): State {
        return State(background, null)
    }

    fun withForeground(color: RGBa): State {
        return State(background, color)
    }

    fun applyBackground(ansi: Ansi) {
        if (background == null || background!!.transparent) {
            ansi.bg(Ansi.Color.DEFAULT)
        } else {
            ansi.bg(background!!)
        }
    }

    fun applyForeground(ansi: Ansi) {
        if (foreground == null || foreground!!.transparent) {
            ansi.fg(Ansi.Color.DEFAULT)
        } else {
            ansi.fg(foreground!!)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is State) return false
        return background == other.background
                && foreground == other.foreground
    }
}