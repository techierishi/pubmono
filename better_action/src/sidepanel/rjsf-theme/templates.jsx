// Custom GitHub Primer Dark Templates for RJSF

export function FieldTemplate(props) {
  const { id, classNames, label, children, errors, help, description, hidden, required, displayLabel, schema } = props;

  if (hidden) return <div style="display:none">{children}</div>;

  // Don't duplicate label for standalone boolean checkbox (CheckboxWidget handles its own label)
  const isCheckbox = schema.type === 'boolean' && !schema.enum;

  return (
    <div className={`rjsf-field ${classNames || ''}`} id={`${id}-wrapper`}>
      {displayLabel && label && !isCheckbox && (
        <label className="rjsf-label" htmlFor={id}>
          {label}
          {required && <span className="rjsf-required" title="Required">*</span>}
        </label>
      )}

      {description && <div className="rjsf-description">{description}</div>}

      <div className="rjsf-control">{children}</div>

      {errors && <div className="rjsf-errors">{errors}</div>}
      {help && <div className="rjsf-help">{help}</div>}
    </div>
  );
}

export function ObjectFieldTemplate(props) {
  const { title, description, properties, required, schema } = props;

  return (
    <div className="rjsf-object-field">
      {title && schema.type === 'object' && properties.length > 0 && (
        <h3 className="rjsf-object-title">
          {title}
          {required && <span className="rjsf-required">*</span>}
        </h3>
      )}
      {description && <div className="rjsf-description">{description}</div>}
      <div className="rjsf-properties-list">
        {properties.map((prop) => (
          <div key={prop.name} className="rjsf-property-item">
            {prop.content}
          </div>
        ))}
      </div>
    </div>
  );
}

export function ArrayFieldTemplate(props) {
  const { title, items, canAdd, onAddClick, required, description } = props;

  return (
    <div className="rjsf-array-field">
      {title && (
        <div className="rjsf-array-header">
          <label className="rjsf-label">
            {title}
            {required && <span className="rjsf-required">*</span>}
          </label>
          {description && <div className="rjsf-description">{description}</div>}
        </div>
      )}

      <div className="rjsf-array-items">
        {items && items.map((item) => (
          <div key={item.key} className="rjsf-array-item">
            <div className="rjsf-array-item-content">{item.children}</div>
            {item.hasRemove && (
              <button
                type="button"
                className="rjsf-btn-danger rjsf-btn-xs"
                onClick={item.onDropIndexClick(item.index)}
                title="Remove item"
              >
                ✕
              </button>
            )}
          </div>
        ))}
      </div>

      {canAdd && (
        <button
          type="button"
          className="rjsf-btn-secondary rjsf-btn-sm"
          onClick={onAddClick}
          style="margin-top: 6px;"
        >
          + Add item
        </button>
      )}
    </div>
  );
}

export function SubmitButton({ uiSchema }) {
  return (
    <div className="rjsf-submit-container">
      <button type="submit" className="sp-btn-run" style="width: 100%; justify-content: center; padding: 8px 16px;">
        ⚡ Dispatch Workflow
      </button>
    </div>
  );
}

export function ErrorListTemplate({ errors }) {
  if (!errors || errors.length === 0) return null;

  return (
    <div className="sp-alert error" style="margin-bottom: 12px; flex-direction: column; align-items: flex-start;">
      <span style="font-weight: 600; font-size: 12px;">Please fix the following errors:</span>
      <ul style="padding-left: 18px; font-size: 11px; margin-top: 4px;">
        {errors.map((error, i) => (
          <li key={i}>{error.stack}</li>
        ))}
      </ul>
    </div>
  );
}

export const customTemplates = {
  FieldTemplate,
  ObjectFieldTemplate,
  ArrayFieldTemplate,
  ButtonTemplates: {
    SubmitButton
  },
  ErrorListTemplate
};
