export interface TableListItemKey {
  category: string;
  name: string;
}

export interface TableListItem {
  category: string;
  name: string;
  value: string;
  properties: string;
  sortOrder: number;
  status: string;
  note: string;
}

export interface TableListPagination {
  total: number;
  pageSize: number;
  current: number;
}

export interface TableListData {
  list: TableListItem[];
  pagination: Partial<TableListPagination>;
}

export interface TableListParams {
  category?: string;
  name?: string;
  status?: string;
  pageSize?: number;
  currentPage?: number;
  filter?: { [key: string]: any[] };
  sorter?: { [key: string]: any };
}
