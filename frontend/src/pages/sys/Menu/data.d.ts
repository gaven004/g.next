export interface TableListItem {
  id: string;
  parentId: string;
  label: string;
  title: string;
  icon: string;
  url: string;
  component: string;
  order: number;
  status: string;
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
  id?: string;
  parentId?: string;
  label?: string;
  title?: string;
  url?: string;
  component?: string;
  status?: string;
  pageSize?: number;
  currentPage?: number;
  filter?: { [key: string]: any[] };
  sorter?: { [key: string]: any };
}
