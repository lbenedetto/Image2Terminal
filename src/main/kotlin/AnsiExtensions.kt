import org.fusesource.jansi.Ansi

fun Ansi.mixed(state: State, top: RGBa, bottom: RGBa): State {
    return if (top.transparent && bottom.transparent) {
        updateIfNeeded(state, state.withDefaultBackground()) {
            a(" ")
        }
    } else if (top == bottom) {
        return updateIfNeeded(state, State(top, top)) {
            a("█")
        }
    } else if (top.transparent) {
        return updateIfNeeded(state, State(background = null, foreground = bottom)) {
            a("▄")
        }
    } else if (bottom.transparent) {
        return updateIfNeeded(state, State(background = null, foreground = top)) {
            a("▀")
        }
    } else {
        updateIfNeeded(state, State(background = top,  foreground = bottom)) {
            a("▄")
        }
    }
}

fun Ansi.updateIfNeeded(currentState: State, newState: State, apply: (Ansi) -> Unit): State {
    if (currentState.background != newState.background) {
        newState.applyBackground(this)
    }
    if (currentState.foreground != newState.foreground) {
        newState.applyForeground(this)
    }
    apply(this)
    return newState
}

fun Ansi.fg(rgb: RGBa) {
    fgRgb(rgb.red, rgb.green, rgb.blue)
}

fun Ansi.bg(rgb: RGBa) {
    bgRgb(rgb.red, rgb.green, rgb.blue)
}