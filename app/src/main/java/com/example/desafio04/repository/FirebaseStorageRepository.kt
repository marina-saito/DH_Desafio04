package com.example.desafio04.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import com.example.desafio04.R
import com.google.android.gms.tasks.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import kotlinx.android.synthetic.main.dialog_loading_image.view.*
import java.util.*
import kotlin.math.roundToInt

class FirebaseStorageRepository {

    var storageReference = FirebaseStorage.getInstance().reference

    fun uploadImg(data: Intent, context: Context): Task<Uri> {
        val imgId = Calendar.getInstance().timeInMillis.toString()

        // Dialog to show upload progress and prevent saving before upload is complete
        val dialogView = View.inflate(context, R.layout.dialog_loading_image, null)
        val dialog = MaterialAlertDialogBuilder(context)
            .setView(dialogView)
            .create()
        dialog.setCanceledOnTouchOutside(false)

        return storageReference.child("game_imgs/$imgId")
            .putFile(data.data!!)
            .addOnProgressListener { (bytesTransferred, totalByteCount) ->
                dialog.show()
                val progress = (100.0 * bytesTransferred) / totalByteCount
                dialogView.tvProgress.text = "${progress.roundToInt()} %"
                dialog.setView(dialogView)
            }
            .addOnCompleteListener {
                dialog.dismiss()
            }
            .continueWithTask {
                storageReference.child("game_imgs/$imgId").downloadUrl
            }
    }
}