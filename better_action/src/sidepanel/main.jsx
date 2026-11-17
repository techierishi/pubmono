import { render } from 'preact';
import { SidepanelApp } from './SidepanelApp.jsx';
import './sidepanel.css';
import 'survey-core/survey-core.fontless.min.css';

render(<SidepanelApp />, document.getElementById('sidepanel-root'));
