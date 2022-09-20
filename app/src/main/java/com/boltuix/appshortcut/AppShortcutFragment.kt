package com.boltuix.appshortcut

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.boltuix.appshortcut.databinding.FragmentAppShortcutBinding


class AppShortcutFragment : Fragment() {

    private var _binding: FragmentAppShortcutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentAppShortcutBinding.inflate(inflater, container, false)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.createShortCut.setOnClickListener {
            createSimpleDynamicShortcut()
        }
        binding.updateShortCut.setOnClickListener {
            updateShortcuts(requireContext())
        }
        binding.removeShortCut.setOnClickListener {
            removeShortcuts(requireContext())
        }



        binding.pinShortCutYoutube.setOnClickListener {

            //Check's that the api version meets the requirements
            if (Build.VERSION.SDK_INT >= 25) {
                //Sets up our shortcuts
                Shortcuts.setUp(requireContext())
            }
            shortcutPin(requireContext(), shortcut_website_id, 0)
        }
        binding.pinShortCutWeb.setOnClickListener {

            //Check's that the api version meets the requirements
            if (Build.VERSION.SDK_INT >= 25) {
                //Sets up our shortcuts
                Shortcuts.setUp(requireContext())
            }
            shortcutPin(requireContext(), shortcut_messages_id, 1)
        }
    }

    // we can change text & icon color
    private fun createSimpleDynamicShortcut() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                Log.d("shortcut01", "init shortcut call")
                var shortcutManager: ShortcutManager? = null
                shortcutManager = requireContext().getSystemService(ShortcutManager::class.java)


                val intent = Intent(context, MainActivity::class.java)
                intent.action = Intent.ACTION_VIEW
                intent.data = Uri.parse("jetpack://view_detail_page")
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                // fragment
                val shortcut1 = ShortcutInfo.Builder(requireContext(), "dShortcut1")
                    .setIntent(intent)
                    .setRank(1)
                    .setLongLabel("My Dynamic Fragment")
                    .setShortLabel("Dynamic Fragment")
                    .setDisabledMessage("Dynamic Fragment disabled")
                    .setIcon(Icon.createWithResource(requireContext(), R.drawable.question))
                    .build()


                val intent2 = Intent(context, MainActivity::class.java)
                intent2.action = Intent.ACTION_VIEW
                intent2.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                //intent
                val shortcut2 = ShortcutInfo.Builder(requireContext(), "dShortcut2")
                    .setIntent(intent2)
                    .setRank(2)
                    .setLongLabel("My Dynamic Activity")
                    .setShortLabel("Dynamic Activity")
                    .setDisabledMessage("Dynamic Activity disabled")
                    .setIcon(Icon.createWithResource(requireContext(), R.drawable.chat))
                    .build()

                shortcutManager!!.dynamicShortcuts = listOf(shortcut1,shortcut2)

            } else {
                Log.d("shortcut01", "no support")
            }
        } catch (g1: Exception) {
            Log.d("shortcut01", "" + g1.message)
        }
    }
    private fun updateShortcuts(context: Context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                val shortcutManager: ShortcutManager =
                    context.getSystemService(ShortcutManager::class.java)
                val thirdShortcut = ShortcutInfo.Builder(context, "dShortcut2")
                    .setShortLabel("dynamic s changed")
                    .setLongLabel("dynamic l changed")
                    .build()
                shortcutManager.updateShortcuts(listOf(thirdShortcut))
            } else {
                Log.d("shortcut01", "no support")
            }
        } catch (g1: Exception) {
            Log.d("shortcut01", "" + g1.message)
        }
    }

    private fun removeShortcuts(context: Context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                val shortcutManager: ShortcutManager =
                    context.getSystemService(ShortcutManager::class.java)
                shortcutManager.disableShortcuts(listOf("dShortcut1","dShortcut2"))
                shortcutManager.removeAllDynamicShortcuts()
            } else {
                Log.d("shortcut01", "no support")
            }
        } catch (g1: Exception) {
            Log.d("shortcut01", "" + g1.message)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun shortcutPin(context: Context, shortcut_id: String, requestCode: Int) {

        val shortcutManager = requireContext().getSystemService(ShortcutManager::class.java)

        if (shortcutManager!!.isRequestPinShortcutSupported) {
            val pinShortcutInfo =
                ShortcutInfo.Builder(context, shortcut_id).build()

            val pinnedShortcutCallbackIntent =
                shortcutManager.createShortcutResultIntent(pinShortcutInfo)

            /* java.lang.IllegalArgumentException: com.boltuix.appshortcut: Targeting S+ (version 31 and above) requires that one of FLAG_IMMUTABLE or FLAG_MUTABLE be specified when creating a PendingIntent.
    Strongly consider using FLAG_IMMUTABLE, only use FLAG_MUTABLE if some functionality depends on the PendingIntent being mutable, e.g. if it needs to be used with inline replies or bubbles.*/
            val successCallback = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getBroadcast(
                    context, /* request code */ requestCode,
                    pinnedShortcutCallbackIntent, /* flags */ PendingIntent.FLAG_IMMUTABLE
                )
            } else {
                PendingIntent.getBroadcast(
                    context, /* request code */ requestCode,
                    pinnedShortcutCallbackIntent, /* flags */ PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
            shortcutManager.requestPinShortcut(
                pinShortcutInfo,
                successCallback.intentSender
            )
        }
    }
}