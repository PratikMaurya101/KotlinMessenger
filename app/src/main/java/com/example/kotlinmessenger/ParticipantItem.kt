package com.example.kotlinmessenger

import android.view.View
import com.example.kotlinmessenger.databinding.UserRowNewchatBinding
import com.xwray.groupie.viewbinding.BindableItem

/*this is our item class which we will pass to the adapter
* this item class extends BindableItem<T> class present in the groupie library
* T is the Layout File user class which will be recycled(generated) in the recycler view
* So we used UserRowNewchatBinding which is our layout file binding class automatically generated
* */
class ParticipantItem: BindableItem<UserRowNewchatBinding>() {

    // [this bind method Uses the binding class UserRowNewchatBinding to initialize the fields]
    override fun bind(viewBinding: UserRowNewchatBinding, position: Int) {
        viewBinding.participantName.text = "Person01"
        viewBinding.Line2.text = "Hi dude!"
        // set the image later
    }

    // [this method returns the layout]
    override fun getLayout() = R.layout.user_row_newchat


    // [initialize ViewBinding]
    override fun initializeViewBinding(view: View): UserRowNewchatBinding {
        return UserRowNewchatBinding.bind(view)
    }
}