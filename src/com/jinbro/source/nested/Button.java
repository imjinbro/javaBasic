package com.jinbro.source.nested;

/*
    [중첩인터페이스]
    - 관계를 명확하게 나타내기위해서 사용한다고함

 */

public class Button {

    OnClickListener listener;

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

    public interface OnClickListener{
        public void onClick();
    }

}

