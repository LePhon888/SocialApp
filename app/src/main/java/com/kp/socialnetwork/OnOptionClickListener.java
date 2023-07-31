package com.kp.socialnetwork;

import com.kp.socialnetwork.data.model.Post;

public interface OnOptionClickListener {
    void onUpdateClicked(Post post);
    void onDeleteClicked(Post post);
}
