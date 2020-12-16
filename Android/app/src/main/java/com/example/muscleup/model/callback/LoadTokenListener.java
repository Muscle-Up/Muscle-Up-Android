package com.example.muscleup.model.callback;

import com.example.muscleup.model.data.Token;

public interface LoadTokenListener {
    void loadToken(Token token);

    void onFail();
}
