package com.ism.interfaces;

/**
 * Created by c161 on 08/10/15.
 */
public interface FragmentListener {
    public void onFragmentAttached(int fragment);
    public void onFragmentDetached(int fragment);
    public void onFragmentResumed(int fragment);
}
