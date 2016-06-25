package com.appdevelopers.seanwade.familymap.person_info;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.appdevelopers.seanwade.familymap.R;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

/**
 * Created by seanwade on 3/21/16.
 */
public class PersonParentViewHolder extends ParentViewHolder {

    public ImageButton parentDropDownArrow;
    public TextView topText;

    public PersonParentViewHolder(View itemView) {
        super(itemView);

        parentDropDownArrow = (ImageButton) itemView.findViewById(R.id.parentEventListArrow);
        topText = (TextView) itemView.findViewById(R.id.topExpandable);
    }
}
