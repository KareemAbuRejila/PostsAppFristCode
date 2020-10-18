package ps.kareemaburejila.postsappfristcode.init

import android.app.Application
import com.joanzapata.iconify.Iconify
import com.joanzapata.iconify.fonts.IoniconsModule

class PostsApplication: Application() {


    override fun onCreate() {
        super.onCreate()
        Iconify.with(IoniconsModule())
    }
}