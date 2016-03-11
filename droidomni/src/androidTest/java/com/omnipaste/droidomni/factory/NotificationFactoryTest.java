package com.omnipaste.droidomni.factory;

import android.app.Notification;
import android.content.Context;

import com.omnipaste.droidomni.DroidOmniApplication_;
import com.omnipaste.omnicommon.dto.ClippingDto;

import junit.framework.TestCase;

import java.util.Date;

/**
 * Created by Sagar Wadhwa on 10-03-2016.
 */
public class NotificationFactoryTest extends TestCase {
    private ClippingDto localPhone,remoteAddress;
    Context context;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        localPhone = new ClippingDto().setContent("+919876543210")
                .setType(ClippingDto.ClippingType.PHONE_NUMBER)
                .setClippingProvider(ClippingDto.ClippingProvider.LOCAL).setCreateAt(new Date())
                .setDeviceId("uniqueId");
        remoteAddress = new ClippingDto(localPhone).setContent("H.No. 22/7, India")
                .setType(ClippingDto.ClippingType.ADDRESS)
                .setClippingProvider(ClippingDto.ClippingProvider.CLOUD);
        context = DroidOmniApplication_.getAppContext();
    }

    public void testCreateLocalNotificationAndVerify() {
        Notification localPhoneNotification = new NotificationFactory(new SmartActionFactory(context))
                .buildSimpleNotification(context, localPhone);

        assertTrue(localPhoneNotification.priority != Notification.PRIORITY_MIN);
    }

    public void testCreateRemoteNotificationAndVerify() {
        Notification remoteAddressNotification = new NotificationFactory(new SmartActionFactory(context))
                .buildSmartActionNotification(context, localPhone);

        assertTrue(remoteAddressNotification.priority != Notification.PRIORITY_MIN);
    }

    public void testCreateBlankNotificationAndVerify() {
        Notification simpleBlankNotification = new NotificationFactory(new SmartActionFactory(context))
                .buildSimpleNotification(context);

        assertTrue(simpleBlankNotification.priority == Notification.PRIORITY_MIN);
    }

    public void testCreateUserNotificationAndVerify() {
        Notification userNotification = new NotificationFactory(new SmartActionFactory(context))
                .buildUserNotification(context,"DroidOmni","Test");

        assertTrue(userNotification.priority == Notification.PRIORITY_MIN);
    }

}
