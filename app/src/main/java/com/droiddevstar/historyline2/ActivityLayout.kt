package com.droiddevstar.historyline2


import androidx.annotation.LayoutRes

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class ActivityLayout(@LayoutRes val getLayoutId: Int)