package course.examples.threading.threadingviewpost;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SimpleThreadingViewPostActivity extends Activity {

	private Bitmap mBitmap;
	private ImageView mImageView;
	private Bitmap nBitmap;
	private ImageView nImageView;
	private Bitmap placeholder;

	//slow down the loading of the image (for demonstration purpose)
	private int mDelay = 10000;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mImageView = (ImageView) findViewById(R.id.imageView);
		nImageView = (ImageView) findViewById(R.id.imageView2);

		placeholder = BitmapFactory.decodeResource(getResources(),
				R.mipmap.placeholder);

		final Button button = (Button) findViewById(R.id.loadButton);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadIcon();
			}
		});

		final Button otherButton = (Button) findViewById(R.id.otherButton);
		otherButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(SimpleThreadingViewPostActivity.this, "I'm Working",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	private double getRand() {
		return 1300 * Math.random();
	}

	// loadIcon is run when the 'Load Icon' button is clicked
	private void loadIcon() {

		// Create a Thread:
		new Thread(new Runnable() {
			@Override

			public void run() {
				// Set the bitmap to the 'painter' image
				mImageView.post(new Runnable() {
					@Override
					public void run() {
						mImageView.setImageBitmap(placeholder);
					}
				});

				try {
					// First, create a delay of 10000 ms
					//  (mDelay was declared above in the Activity class)
					mDelay = (int)getRand();
					Thread.sleep(mDelay);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// Set the bitmap to the 'painter' image
				mBitmap = BitmapFactory.decodeResource(getResources(),
						R.mipmap.dog);

				// Now set the ImageView mBitmap
				// The post method causes the Runnable to be added to the message queue
				// The runnable will be run on the user interface thread
				mImageView.post(new Runnable() {
					@Override
					public void run() {
						mImageView.setImageBitmap(mBitmap);
					}
				});
			}
		}).start();

		// Create a Thread:
		new Thread(new Runnable() {
			@Override

			public void run() {
				nImageView.post(new Runnable() {
					@Override
					public void run() {
						nImageView.setImageBitmap(placeholder);
					}
				});
				try {
					// First, create a delay of 10000 ms
					//  (mDelay was declared above in the Activity class)
					mDelay = (int)getRand();
					Thread.sleep(mDelay);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// Set the bitmap to the 'painter' image
				nBitmap = BitmapFactory.decodeResource(getResources(),
						R.mipmap.cat);

				// Now set the ImageView mBitmap
				// The post method causes the Runnable to be added to the message queue
				// The runnable will be run on the user interface thread
				nImageView.post(new Runnable() {
					@Override
					public void run() {
						nImageView.setImageBitmap(nBitmap);
					}
				});
			}
		}).start();
	}
}
