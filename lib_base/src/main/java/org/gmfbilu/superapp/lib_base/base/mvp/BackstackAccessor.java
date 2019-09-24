package org.gmfbilu.superapp.lib_base.base.mvp;

import androidx.fragment.app.Fragment;

import java.io.PrintWriter;
import java.io.StringWriter;

public class BackstackAccessor {

    private BackstackAccessor() {
        throw new IllegalStateException("Not instantiatable");
    }

    /**
     * Checks whether or not a given fragment is on the backstack of the fragment manager (could also
     * be on top of the backstack and hence visible)
     *
     * @param fragment The fragment you want to check if its on the back stack
     * @return true, if the given Fragment is on the back stack, otherwise false (not on the back
     * stack)
     */
 /*   public static boolean isFragmentOnBackStack(Fragment fragment) {
        try {
            return fragment.isInBackStack();
        } catch (IllegalAccessError e) {
            return isInBackStackAndroidX(fragment);
        }
    }*/

    /**
     * As of version 1.0 of AndroidX - fragment.isInBackStack() is package private which leads to
     * an IllegalAccessError being thrown when trying to use it.
     * This method is a temporary workaround until the issue is resolved in AndroidX.
     */
    public static boolean isInBackStackAndroidX(final Fragment fragment) {
        final StringWriter writer = new StringWriter();
        fragment.dump("", null, new PrintWriter(writer), null);
        final String dump = writer.toString();
        return !dump.contains("mBackStackNesting=0");
    }
/*
  public static boolean isFragmentOnBackStack(Fragment fragment) {
    FragmentManager fragmentManager = fragment.getFragmentManager();
    int backStackSize = fragmentManager.getBackStackEntryCount();
    for (int i = 0; i < backStackSize; i++) {
      BackStackRecord stackEntry = (BackStackRecord) fragmentManager.getBackStackEntryAt(i);
      int opsCount = stackEntry.mOps == null ? 0 : stackEntry.mOps.size();
      if (opsCount > 0) {
        BackStackRecord.Op op = stackEntry.mOps.get(opsCount-1);
        if (op.fragment == fragment) {
          return true;
        }
      }
    }
    return false;
  }
*/

/*
  private static boolean findNext(@NonNull Fragment toFind, @Nullable BackStackRecord.Op next) {
    if (next == null) {
      return false;
    }
    if (toFind == next.fragment) {
      return true;
    } else {
      return findNext(toFind, next.next);
    }
  }
  private static boolean findPrevious(@NonNull Fragment toFind,
      @Nullable BackStackRecord.Op previous) {
    if (previous == null) {
      return false;
    }
    if (toFind == previous.fragment) {
      return true;
    } else {
      return findPrevious(toFind, previous.prev);
    }
  }
  */
}
