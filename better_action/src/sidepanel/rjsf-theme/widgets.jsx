// Custom GitHub Primer Dark Widgets for RJSF

export function TextWidget(props) {
  const { id, value, required, disabled, readonly, onChange, onBlur, onFocus, placeholder, rawErrors, schema } = props;
  const inputType = schema.type === 'number' || schema.type === 'integer' ? 'number' : (props.type || 'text');

  return (
    <input
      id={id}
      type={inputType}
      className={`rjsf-input ${rawErrors?.length ? 'rjsf-input-error' : ''}`}
      value={value === undefined || value === null ? '' : value}
      placeholder={placeholder || ''}
      required={required}
      disabled={disabled || readonly}
      onInput={(e) => onChange(inputType === 'number' ? (e.target.value === '' ? undefined : Number(e.target.value)) : e.target.value)}
      onBlur={onBlur && ((e) => onBlur(id, e.target.value))}
      onFocus={onFocus && ((e) => onFocus(id, e.target.value))}
    />
  );
}

export function TextareaWidget(props) {
  const { id, value, required, disabled, readonly, onChange, onBlur, onFocus, placeholder, options, rawErrors } = props;
  const rows = options?.rows || 4;

  return (
    <textarea
      id={id}
      className={`rjsf-textarea ${rawErrors?.length ? 'rjsf-input-error' : ''}`}
      rows={rows}
      value={value === undefined || value === null ? '' : value}
      placeholder={placeholder || ''}
      required={required}
      disabled={disabled || readonly}
      onInput={(e) => onChange(e.target.value)}
      onBlur={onBlur && ((e) => onBlur(id, e.target.value))}
      onFocus={onFocus && ((e) => onFocus(id, e.target.value))}
    />
  );
}

export function SelectWidget(props) {
  const { id, options, value, required, disabled, readonly, onChange, onBlur, onFocus, placeholder } = props;
  const { enumOptions, enumDisabled } = options;

  return (
    <select
      id={id}
      className="rjsf-select"
      value={value === undefined ? '' : value}
      required={required}
      disabled={disabled || readonly}
      onChange={(e) => onChange(e.target.value)}
      onBlur={onBlur && ((e) => onBlur(id, e.target.value))}
      onFocus={onFocus && ((e) => onFocus(id, e.target.value))}
    >
      {placeholder && <option value="">{placeholder}</option>}
      {enumOptions && enumOptions.map(({ value: val, label }, i) => {
        const disabledItem = Array.isArray(enumDisabled) && enumDisabled.indexOf(val) !== -1;
        return (
          <option key={i} value={val} disabled={disabledItem}>
            {label}
          </option>
        );
      })}
    </select>
  );
}

export function CheckboxWidget(props) {
  const { id, value, disabled, readonly, label, onChange } = props;

  return (
    <label className="rjsf-checkbox-label" htmlFor={id}>
      <input
        type="checkbox"
        id={id}
        className="rjsf-checkbox"
        checked={typeof value === 'undefined' ? false : Boolean(value)}
        disabled={disabled || readonly}
        onChange={(e) => onChange(e.target.checked)}
      />
      <span className="rjsf-checkbox-text">{label}</span>
    </label>
  );
}

export function CheckboxesWidget(props) {
  const { id, options, value, disabled, readonly, onChange } = props;
  const { enumOptions, enumDisabled } = options;

  const currentValues = Array.isArray(value) ? value : [];

  function handleToggle(optionValue) {
    const nextValues = currentValues.includes(optionValue)
      ? currentValues.filter((v) => v !== optionValue)
      : [...currentValues, optionValue];
    onChange(nextValues);
  }

  return (
    <div className="rjsf-checkboxes-group" id={id}>
      {enumOptions && enumOptions.map(({ value: val, label }, i) => {
        const isChecked = currentValues.includes(val);
        const itemDisabled = Array.isArray(enumDisabled) && enumDisabled.indexOf(val) !== -1;
        return (
          <label key={i} className="rjsf-checkbox-label">
            <input
              type="checkbox"
              className="rjsf-checkbox"
              checked={isChecked}
              disabled={disabled || readonly || itemDisabled}
              onChange={() => handleToggle(val)}
            />
            <span className="rjsf-checkbox-text">{label}</span>
          </label>
        );
      })}
    </div>
  );
}

export function RadioWidget(props) {
  const { id, options, value, required, disabled, readonly, onChange } = props;
  const { enumOptions, enumDisabled } = options;

  return (
    <div className="rjsf-radios-group" id={id}>
      {enumOptions && enumOptions.map(({ value: val, label }, i) => {
        const isChecked = value === val;
        const itemDisabled = Array.isArray(enumDisabled) && enumDisabled.indexOf(val) !== -1;
        return (
          <label key={i} className="rjsf-radio-label">
            <input
              type="radio"
              className="rjsf-radio"
              name={id}
              value={val}
              checked={isChecked}
              required={required}
              disabled={disabled || readonly || itemDisabled}
              onChange={() => onChange(val)}
            />
            <span className="rjsf-radio-text">{label}</span>
          </label>
        );
      })}
    </div>
  );
}

export const customWidgets = {
  TextWidget,
  TextareaWidget,
  SelectWidget,
  CheckboxWidget,
  CheckboxesWidget,
  RadioWidget
};
