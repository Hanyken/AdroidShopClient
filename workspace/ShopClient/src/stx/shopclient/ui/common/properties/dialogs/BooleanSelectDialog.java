package stx.shopclient.ui.common.properties.dialogs;

import org.apache.commons.lang3.SerializationUtils;

import stx.shopclient.entity.properties.BooleanPropertyDescriptor;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

public class BooleanSelectDialog extends DialogFragment
{
	DialogResultProcessor _resultProcessor;
	private BooleanPropertyDescriptor _property;
	private BooleanPropertyDescriptor _propertyCopy;
	private View _itemView;

	public void setResultProcessor(DialogResultProcessor processor)
	{
		_resultProcessor = processor;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{

		_propertyCopy = SerializationUtils.clone(_property);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle(_property.getTitle());

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				_property.setValueDefined(_propertyCopy.isValueDefined());
				_property.setCurrentValue(_propertyCopy.getCurrentValue());
				_resultProcessor.onPositiveDialogResult(_itemView);
			}
		});

		builder.setNegativeButton("Отмена",
				new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						_resultProcessor.onNegativeDialogResult(_itemView);
					}
				});

		String[] items = new String[3];
		items[0] = "Не задано";
		items[1] = "Да";
		items[2] = "Нет";

		int checkedItem = _property.isValueDefined() ? (_property
				.getCurrentValue() ? 1 : 2) : 0;

		builder.setSingleChoiceItems(items, checkedItem,
				new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						switch (which)
						{
						case 0:
							_propertyCopy.setValueDefined(false);
							break;
						case 1:
							_propertyCopy.setValueDefined(true);
							_propertyCopy.setCurrentValue(true);
							break;
						case 2:
							_propertyCopy.setValueDefined(true);
							_propertyCopy.setCurrentValue(false);

						default:
							break;
						}
					}
				});

		return builder.create();
	}

	public BooleanPropertyDescriptor getProperty()
	{
		return _property;
	}

	public void setProperty(BooleanPropertyDescriptor property)
	{
		_property = property;
	}

	public View getItemView()
	{
		return _itemView;
	}

	public void setItemView(View itemView)
	{
		_itemView = itemView;
	}
}
