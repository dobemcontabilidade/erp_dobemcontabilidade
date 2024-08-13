import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IClasseCnae } from '../classe-cnae.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../classe-cnae.test-samples';

import { ClasseCnaeService } from './classe-cnae.service';

const requireRestSample: IClasseCnae = {
  ...sampleWithRequiredData,
};

describe('ClasseCnae Service', () => {
  let service: ClasseCnaeService;
  let httpMock: HttpTestingController;
  let expectedResult: IClasseCnae | IClasseCnae[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ClasseCnaeService);
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

    it('should create a ClasseCnae', () => {
      const classeCnae = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(classeCnae).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ClasseCnae', () => {
      const classeCnae = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(classeCnae).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ClasseCnae', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ClasseCnae', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ClasseCnae', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addClasseCnaeToCollectionIfMissing', () => {
      it('should add a ClasseCnae to an empty array', () => {
        const classeCnae: IClasseCnae = sampleWithRequiredData;
        expectedResult = service.addClasseCnaeToCollectionIfMissing([], classeCnae);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(classeCnae);
      });

      it('should not add a ClasseCnae to an array that contains it', () => {
        const classeCnae: IClasseCnae = sampleWithRequiredData;
        const classeCnaeCollection: IClasseCnae[] = [
          {
            ...classeCnae,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addClasseCnaeToCollectionIfMissing(classeCnaeCollection, classeCnae);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ClasseCnae to an array that doesn't contain it", () => {
        const classeCnae: IClasseCnae = sampleWithRequiredData;
        const classeCnaeCollection: IClasseCnae[] = [sampleWithPartialData];
        expectedResult = service.addClasseCnaeToCollectionIfMissing(classeCnaeCollection, classeCnae);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(classeCnae);
      });

      it('should add only unique ClasseCnae to an array', () => {
        const classeCnaeArray: IClasseCnae[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const classeCnaeCollection: IClasseCnae[] = [sampleWithRequiredData];
        expectedResult = service.addClasseCnaeToCollectionIfMissing(classeCnaeCollection, ...classeCnaeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const classeCnae: IClasseCnae = sampleWithRequiredData;
        const classeCnae2: IClasseCnae = sampleWithPartialData;
        expectedResult = service.addClasseCnaeToCollectionIfMissing([], classeCnae, classeCnae2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(classeCnae);
        expect(expectedResult).toContain(classeCnae2);
      });

      it('should accept null and undefined values', () => {
        const classeCnae: IClasseCnae = sampleWithRequiredData;
        expectedResult = service.addClasseCnaeToCollectionIfMissing([], null, classeCnae, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(classeCnae);
      });

      it('should return initial array if no ClasseCnae is added', () => {
        const classeCnaeCollection: IClasseCnae[] = [sampleWithRequiredData];
        expectedResult = service.addClasseCnaeToCollectionIfMissing(classeCnaeCollection, undefined, null);
        expect(expectedResult).toEqual(classeCnaeCollection);
      });
    });

    describe('compareClasseCnae', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareClasseCnae(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareClasseCnae(entity1, entity2);
        const compareResult2 = service.compareClasseCnae(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareClasseCnae(entity1, entity2);
        const compareResult2 = service.compareClasseCnae(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareClasseCnae(entity1, entity2);
        const compareResult2 = service.compareClasseCnae(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
