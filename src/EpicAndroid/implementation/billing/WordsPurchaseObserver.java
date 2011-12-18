package com.epic.framework.implementation.billing;

import android.os.Handler;

import com.epic.framework.common.Ui.EpicClickListener;
import com.epic.framework.common.Ui.EpicDialogBuilder;
import com.epic.framework.common.Ui.EpicNotification;
import com.epic.framework.common.Ui.EpicPlatform;
import com.epic.framework.common.util.EpicLog;
import com.epic.framework.implementation.EpicAndroidActivity;
import com.epic.framework.implementation.billing.EpicBillingConstants.PurchaseState;
import com.epic.framework.implementation.billing.EpicBillingConstants.ResponseCode;
import com.epic.framework.implementation.billing.EpicInAppService.RequestPurchase;
import com.epic.framework.implementation.billing.EpicInAppService.RestoreTransactions;

/**
 * A {@link PurchaseObserver} is used to get callbacks when Android Market sends
 * messages to this application so that we can update the UI.
 */
public class WordsPurchaseObserver extends EpicBillingPurchaseObserver {
  
	public WordsPurchaseObserver(Handler handler) {
        super(EpicAndroidActivity.getCurrentAndroidActivity(), handler);
    }

    @Override
    public void onBillingSupported(boolean supported) {
        if (EpicBillingConstants.DEBUG) {
            EpicLog.i("supported: " + supported);
        }
        if (!supported) {
            //throw new EpicRuntimeException("BILLING NOT SUPPORTED--HANDLE THIS CASE");
        	EpicPlatform.doToastNotification(new EpicNotification("In-App Purchase Not Supported", new String[] { "It appears in-app purchases are not supported on your device.", "Please contact android@realcasualgames.com for other options"}, EpicImages.icon, 8));
        }
    }

    @Override
    public void onPurchaseStateChange(PurchaseState purchaseState, String itemId, int quantity, long purchaseTime, String developerPayload) {
        if (EpicBillingConstants.DEBUG) {
            EpicLog.i("onPurchaseStateChange() itemId: " + itemId + " " + purchaseState);
        }

//        if (developerPayload == null) {
//            logProductActivity(itemId, purchaseState.toString());
//        } else {
//            logProductActivity(itemId, purchaseState + "\n\t" + developerPayload);
//        }

        if (purchaseState == PurchaseState.PURCHASED) {
        	if(itemId.equals("tokens_sm")) {
        		PlayerState.updateLocalTokens(25000);
        	} else if(itemId.equals("tokens_md")) {
        		PlayerState.updateLocalTokens(60000);
        	} else if(itemId.equals("tokens_lg")) {
        		PlayerState.updateLocalTokens(100000);
        	} else if(itemId.equals("tokens_max")) {
        		PlayerState.updateLocalTokens(99999999);
        	} else if(itemId.equals("android.test.purchased")) {
        		PlayerState.updateLocalTokens(1000);
        	}
        	
        	PlayerState.markPlayerAsPremium();
	        	
        	new EpicDialogBuilder().
        	setMessage("Your purchase was successful! Your tokens have been applied immediately.").
        	setPositiveButton("OK", EpicClickListener.NoOp).
        	show();
        	
        	EpicPlatform.changeScreen(new ScreenNursery());
        }
    }

    @Override
    public void onRequestPurchaseResponse(RequestPurchase request,
            ResponseCode responseCode) {
        if (EpicBillingConstants.DEBUG) {
            EpicLog.d(request.mProductId + ": " + responseCode);
        }
        if (responseCode == ResponseCode.RESULT_OK) {
            if (EpicBillingConstants.DEBUG) {
                EpicLog.i("purchase was successfully sent to server");
            }
            EpicLog.i("sending purchase request");
        } else if (responseCode == ResponseCode.RESULT_USER_CANCELED) {
            if (EpicBillingConstants.DEBUG) {
                EpicLog.i("user canceled purchase");
            }
            EpicLog.i("dismissed purchase dialog");
        } else {
            if (EpicBillingConstants.DEBUG) {
                EpicLog.i("purchase failed");
            }
            EpicLog.i("request purchase returned " + responseCode);
        }
    }

	public void onRestoreTransactionsResponse(RestoreTransactions request, ResponseCode responseCode) {
		EpicLog.e("onRestoreTransactionsResponse not supported.");
	}
}