import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAtividadeEmpresa } from '../atividade-empresa.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../atividade-empresa.test-samples';

import { AtividadeEmpresaService } from './atividade-empresa.service';

const requireRestSample: IAtividadeEmpresa = {
  ...sampleWithRequiredData,
};

describe('AtividadeEmpresa Service', () => {
  let service: AtividadeEmpresaService;
  let httpMock: HttpTestingController;
  let expectedResult: IAtividadeEmpresa | IAtividadeEmpresa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AtividadeEmpresaService);
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

    it('should create a AtividadeEmpresa', () => {
      const atividadeEmpresa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(atividadeEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AtividadeEmpresa', () => {
      const atividadeEmpresa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(atividadeEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AtividadeEmpresa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AtividadeEmpresa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AtividadeEmpresa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAtividadeEmpresaToCollectionIfMissing', () => {
      it('should add a AtividadeEmpresa to an empty array', () => {
        const atividadeEmpresa: IAtividadeEmpresa = sampleWithRequiredData;
        expectedResult = service.addAtividadeEmpresaToCollectionIfMissing([], atividadeEmpresa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(atividadeEmpresa);
      });

      it('should not add a AtividadeEmpresa to an array that contains it', () => {
        const atividadeEmpresa: IAtividadeEmpresa = sampleWithRequiredData;
        const atividadeEmpresaCollection: IAtividadeEmpresa[] = [
          {
            ...atividadeEmpresa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAtividadeEmpresaToCollectionIfMissing(atividadeEmpresaCollection, atividadeEmpresa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AtividadeEmpresa to an array that doesn't contain it", () => {
        const atividadeEmpresa: IAtividadeEmpresa = sampleWithRequiredData;
        const atividadeEmpresaCollection: IAtividadeEmpresa[] = [sampleWithPartialData];
        expectedResult = service.addAtividadeEmpresaToCollectionIfMissing(atividadeEmpresaCollection, atividadeEmpresa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(atividadeEmpresa);
      });

      it('should add only unique AtividadeEmpresa to an array', () => {
        const atividadeEmpresaArray: IAtividadeEmpresa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const atividadeEmpresaCollection: IAtividadeEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addAtividadeEmpresaToCollectionIfMissing(atividadeEmpresaCollection, ...atividadeEmpresaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const atividadeEmpresa: IAtividadeEmpresa = sampleWithRequiredData;
        const atividadeEmpresa2: IAtividadeEmpresa = sampleWithPartialData;
        expectedResult = service.addAtividadeEmpresaToCollectionIfMissing([], atividadeEmpresa, atividadeEmpresa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(atividadeEmpresa);
        expect(expectedResult).toContain(atividadeEmpresa2);
      });

      it('should accept null and undefined values', () => {
        const atividadeEmpresa: IAtividadeEmpresa = sampleWithRequiredData;
        expectedResult = service.addAtividadeEmpresaToCollectionIfMissing([], null, atividadeEmpresa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(atividadeEmpresa);
      });

      it('should return initial array if no AtividadeEmpresa is added', () => {
        const atividadeEmpresaCollection: IAtividadeEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addAtividadeEmpresaToCollectionIfMissing(atividadeEmpresaCollection, undefined, null);
        expect(expectedResult).toEqual(atividadeEmpresaCollection);
      });
    });

    describe('compareAtividadeEmpresa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAtividadeEmpresa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAtividadeEmpresa(entity1, entity2);
        const compareResult2 = service.compareAtividadeEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAtividadeEmpresa(entity1, entity2);
        const compareResult2 = service.compareAtividadeEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAtividadeEmpresa(entity1, entity2);
        const compareResult2 = service.compareAtividadeEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
