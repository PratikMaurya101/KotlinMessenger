package com.example.kotlinmessenger.models

import android.view.View
import com.example.kotlinmessenger.R
import com.example.kotlinmessenger.databinding.UserRowNewchatBinding
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem

/*this is our item class which we will pass to the adapter
* this item class extends BindableItem<T> class present in the groupie library
* T is the Layout File user class which will be recycled(generated) in the recycler view
* So we used UserRowNewchatBinding which is our layout file binding class automatically generated
* */
class ParticipantItem(val user: User): BindableItem<UserRowNewchatBinding>() {

    // [this bind method Uses the binding class UserRowNewchatBinding to initialize the fields]
    override fun bind(viewBinding: UserRowNewchatBinding, position: Int) {
        viewBinding.participantName.text = user.username
        viewBinding.Line2.text = user.uid
        Picasso.get().load(user.profileImageUrl).into(viewBinding.participantImage)
    }

    // [this method returns the layout]
    override fun getLayout() = R.layout.user_row_newchat


    // [initialize ViewBinding]
    override fun initializeViewBinding(view: View): UserRowNewchatBinding {
        return UserRowNewchatBinding.bind(view)
    }
}