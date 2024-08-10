import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDocumentoTarefa } from '../documento-tarefa.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../documento-tarefa.test-samples';

import { DocumentoTarefaService } from './documento-tarefa.service';

const requireRestSample: IDocumentoTarefa = {
  ...sampleWithRequiredData,
};

describe('DocumentoTarefa Service', () => {
  let service: DocumentoTarefaService;
  let httpMock: HttpTestingController;
  let expectedResult: IDocumentoTarefa | IDocumentoTarefa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DocumentoTarefaService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a DocumentoTarefa', () => {
      const documentoTarefa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(documentoTarefa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DocumentoTarefa', () => {
      const documentoTarefa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(documentoTarefa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DocumentoTarefa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DocumentoTarefa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DocumentoTarefa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDocumentoTarefaToCollectionIfMissing', () => {
      it('should add a DocumentoTarefa to an empty array', () => {
        const documentoTarefa: IDocumentoTarefa = sampleWithRequiredData;
        expectedResult = service.addDocumentoTarefaToCollectionIfMissing([], documentoTarefa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(documentoTarefa);
      });

      it('should not add a DocumentoTarefa to an array that contains it', () => {
        const documentoTarefa: IDocumentoTarefa = sampleWithRequiredData;
        const documentoTarefaCollection: IDocumentoTarefa[] = [
          {
            ...documentoTarefa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDocumentoTarefaToCollectionIfMissing(documentoTarefaCollection, documentoTarefa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DocumentoTarefa to an array that doesn't contain it", () => {
        const documentoTarefa: IDocumentoTarefa = sampleWithRequiredData;
        const documentoTarefaCollection: IDocumentoTarefa[] = [sampleWithPartialData];
        expectedResult = service.addDocumentoTarefaToCollectionIfMissing(documentoTarefaCollection, documentoTarefa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(documentoTarefa);
      });

      it('should add only unique DocumentoTarefa to an array', () => {
        const documentoTarefaArray: IDocumentoTarefa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const documentoTarefaCollection: IDocumentoTarefa[] = [sampleWithRequiredData];
        expectedResult = service.addDocumentoTarefaToCollectionIfMissing(documentoTarefaCollection, ...documentoTarefaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const documentoTarefa: IDocumentoTarefa = sampleWithRequiredData;
        const documentoTarefa2: IDocumentoTarefa = sampleWithPartialData;
        expectedResult = service.addDocumentoTarefaToCollectionIfMissing([], documentoTarefa, documentoTarefa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(documentoTarefa);
        expect(expectedResult).toContain(documentoTarefa2);
      });

      it('should accept null and undefined values', () => {
        const documentoTarefa: IDocumentoTarefa = sampleWithRequiredData;
        expectedResult = service.addDocumentoTarefaToCollectionIfMissing([], null, documentoTarefa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(documentoTarefa);
      });

      it('should return initial array if no DocumentoTarefa is added', () => {
        const documentoTarefaCollection: IDocumentoTarefa[] = [sampleWithRequiredData];
        expectedResult = service.addDocumentoTarefaToCollectionIfMissing(documentoTarefaCollection, undefined, null);
        expect(expectedResult).toEqual(documentoTarefaCollection);
      });
    });

    describe('compareDocumentoTarefa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDocumentoTarefa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDocumentoTarefa(entity1, entity2);
        const compareResult2 = service.compareDocumentoTarefa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDocumentoTarefa(entity1, entity2);
        const compareResult2 = service.compareDocumentoTarefa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDocumentoTarefa(entity1, entity2);
        const compareResult2 = service.compareDocumentoTarefa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
