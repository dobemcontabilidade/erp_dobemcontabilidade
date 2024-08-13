import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISubclasseCnae } from '../subclasse-cnae.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../subclasse-cnae.test-samples';

import { SubclasseCnaeService } from './subclasse-cnae.service';

const requireRestSample: ISubclasseCnae = {
  ...sampleWithRequiredData,
};

describe('SubclasseCnae Service', () => {
  let service: SubclasseCnaeService;
  let httpMock: HttpTestingController;
  let expectedResult: ISubclasseCnae | ISubclasseCnae[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SubclasseCnaeService);
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

    it('should create a SubclasseCnae', () => {
      const subclasseCnae = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(subclasseCnae).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SubclasseCnae', () => {
      const subclasseCnae = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(subclasseCnae).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SubclasseCnae', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SubclasseCnae', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SubclasseCnae', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSubclasseCnaeToCollectionIfMissing', () => {
      it('should add a SubclasseCnae to an empty array', () => {
        const subclasseCnae: ISubclasseCnae = sampleWithRequiredData;
        expectedResult = service.addSubclasseCnaeToCollectionIfMissing([], subclasseCnae);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subclasseCnae);
      });

      it('should not add a SubclasseCnae to an array that contains it', () => {
        const subclasseCnae: ISubclasseCnae = sampleWithRequiredData;
        const subclasseCnaeCollection: ISubclasseCnae[] = [
          {
            ...subclasseCnae,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSubclasseCnaeToCollectionIfMissing(subclasseCnaeCollection, subclasseCnae);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SubclasseCnae to an array that doesn't contain it", () => {
        const subclasseCnae: ISubclasseCnae = sampleWithRequiredData;
        const subclasseCnaeCollection: ISubclasseCnae[] = [sampleWithPartialData];
        expectedResult = service.addSubclasseCnaeToCollectionIfMissing(subclasseCnaeCollection, subclasseCnae);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subclasseCnae);
      });

      it('should add only unique SubclasseCnae to an array', () => {
        const subclasseCnaeArray: ISubclasseCnae[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const subclasseCnaeCollection: ISubclasseCnae[] = [sampleWithRequiredData];
        expectedResult = service.addSubclasseCnaeToCollectionIfMissing(subclasseCnaeCollection, ...subclasseCnaeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const subclasseCnae: ISubclasseCnae = sampleWithRequiredData;
        const subclasseCnae2: ISubclasseCnae = sampleWithPartialData;
        expectedResult = service.addSubclasseCnaeToCollectionIfMissing([], subclasseCnae, subclasseCnae2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subclasseCnae);
        expect(expectedResult).toContain(subclasseCnae2);
      });

      it('should accept null and undefined values', () => {
        const subclasseCnae: ISubclasseCnae = sampleWithRequiredData;
        expectedResult = service.addSubclasseCnaeToCollectionIfMissing([], null, subclasseCnae, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subclasseCnae);
      });

      it('should return initial array if no SubclasseCnae is added', () => {
        const subclasseCnaeCollection: ISubclasseCnae[] = [sampleWithRequiredData];
        expectedResult = service.addSubclasseCnaeToCollectionIfMissing(subclasseCnaeCollection, undefined, null);
        expect(expectedResult).toEqual(subclasseCnaeCollection);
      });
    });

    describe('compareSubclasseCnae', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSubclasseCnae(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSubclasseCnae(entity1, entity2);
        const compareResult2 = service.compareSubclasseCnae(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSubclasseCnae(entity1, entity2);
        const compareResult2 = service.compareSubclasseCnae(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSubclasseCnae(entity1, entity2);
        const compareResult2 = service.compareSubclasseCnae(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
