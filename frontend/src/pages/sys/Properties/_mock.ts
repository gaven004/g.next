// eslint-disable-next-line import/no-extraneous-dependencies
import {Request, Response} from 'express';
import {parse} from 'url';
import {TableListItem, TableListParams} from './data.d';

export default {
  'GET /api/sys/properties': {
    "success": true,
    "data": [
      {"category": "Test", "name": "Hello", "value": "World", "sortOrder": 0, "status": "VALID"},
      {"category": "Test", "name": "Hello2", "value": "World", "sortOrder": 0, "status": "INVALID"},
      {"category": "Test", "name": "Hi", "value": "World", "sortOrder": 0, "status": "VALID"}],
    "current": 1,
    "pageSize": 10,
    "total": 2
  }
}
;
