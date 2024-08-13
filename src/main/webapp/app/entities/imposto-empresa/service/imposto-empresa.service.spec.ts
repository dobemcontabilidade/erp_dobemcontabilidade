import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IImpostoEmpresa } from '../imposto-empresa.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../imposto-empresa.test-samples';

import { ImpostoEmpresaService } from './imposto-empresa.service';

const requireRestSample: IImpostoEmpresa = {
  ...sampleWithRequiredData,
};

describe('ImpostoEmpresa Service', () => {
  let service: ImpostoEmpresaService;
  let httpMock: HttpTestingController;
  let expectedResult: IImpostoEmpresa | IImpostoEmpresa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ImpostoEmpresaService);
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

    it('should create a ImpostoEmpresa', () => {
      const impostoEmpresa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(impostoEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ImpostoEmpresa', () => {
      const impostoEmpresa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(impostoEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ImpostoEmpresa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ImpostoEmpresa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ImpostoEmpresa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addImpostoEmpresaToCollectionIfMissing', () => {
      it('should add a ImpostoEmpresa to an empty array', () => {
        const impostoEmpresa: IImpostoEmpresa = sampleWithRequiredData;
        expectedResult = service.addImpostoEmpresaToCollectionIfMissing([], impostoEmpresa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(impostoEmpresa);
      });

      it('should not add a ImpostoEmpresa to an array that contains it', () => {
        const impostoEmpresa: IImpostoEmpresa = sampleWithRequiredData;
        const impostoEmpresaCollection: IImpostoEmpresa[] = [
          {
            ...impostoEmpresa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addImpostoEmpresaToCollectionIfMissing(impostoEmpresaCollection, impostoEmpresa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ImpostoEmpresa to an array that doesn't contain it", () => {
        const impostoEmpresa: IImpostoEmpresa = sampleWithRequiredData;
        const impostoEmpresaCollection: IImpostoEmpresa[] = [sampleWithPartialData];
        expectedResult = service.addImpostoEmpresaToCollectionIfMissing(impostoEmpresaCollection, impostoEmpresa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(impostoEmpresa);
      });

      it('should add only unique ImpostoEmpresa to an array', () => {
        const impostoEmpresaArray: IImpostoEmpresa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const impostoEmpresaCollection: IImpostoEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addImpostoEmpresaToCollectionIfMissing(impostoEmpresaCollection, ...impostoEmpresaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const impostoEmpresa: IImpostoEmpresa = sampleWithRequiredData;
        const impostoEmpresa2: IImpostoEmpresa = sampleWithPartialData;
        expectedResult = service.addImpostoEmpresaToCollectionIfMissing([], impostoEmpresa, impostoEmpresa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(impostoEmpresa);
        expect(expectedResult).toContain(impostoEmpresa2);
      });

      it('should accept null and undefined values', () => {
        const impostoEmpresa: IImpostoEmpresa = sampleWithRequiredData;
        expectedResult = service.addImpostoEmpresaToCollectionIfMissing([], null, impostoEmpresa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(impostoEmpresa);
      });

      it('should return initial array if no ImpostoEmpresa is added', () => {
        const impostoEmpresaCollection: IImpostoEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addImpostoEmpresaToCollectionIfMissing(impostoEmpresaCollection, undefined, null);
        expect(expectedResult).toEqual(impostoEmpresaCollection);
      });
    });

    describe('compareImpostoEmpresa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareImpostoEmpresa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareImpostoEmpresa(entity1, entity2);
        const compareResult2 = service.compareImpostoEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareImpostoEmpresa(entity1, entity2);
        const compareResult2 = service.compareImpostoEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareImpostoEmpresa(entity1, entity2);
        const compareResult2 = service.compareImpostoEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
