import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../termo-de-adesao.test-samples';

import { TermoDeAdesaoFormService } from './termo-de-adesao-form.service';

describe('TermoDeAdesao Form Service', () => {
  let service: TermoDeAdesaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TermoDeAdesaoFormService);
  });

  describe('Service methods', () => {
    describe('createTermoDeAdesaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTermoDeAdesaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titulo: expect.any(Object),
            descricao: expect.any(Object),
            urlDoc: expect.any(Object),
          }),
        );
      });

      it('passing ITermoDeAdesao should create a new form with FormGroup', () => {
        const formGroup = service.createTermoDeAdesaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titulo: expect.any(Object),
            descricao: expect.any(Object),
            urlDoc: expect.any(Object),
          }),
        );
      });
    });

    describe('getTermoDeAdesao', () => {
      it('should return NewTermoDeAdesao for default TermoDeAdesao initial value', () => {
        const formGroup = service.createTermoDeAdesaoFormGroup(sampleWithNewData);

        const termoDeAdesao = service.getTermoDeAdesao(formGroup) as any;

        expect(termoDeAdesao).toMatchObject(sampleWithNewData);
      });

      it('should return NewTermoDeAdesao for empty TermoDeAdesao initial value', () => {
        const formGroup = service.createTermoDeAdesaoFormGroup();

        const termoDeAdesao = service.getTermoDeAdesao(formGroup) as any;

        expect(termoDeAdesao).toMatchObject({});
      });

      it('should return ITermoDeAdesao', () => {
        const formGroup = service.createTermoDeAdesaoFormGroup(sampleWithRequiredData);

        const termoDeAdesao = service.getTermoDeAdesao(formGroup) as any;

        expect(termoDeAdesao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITermoDeAdesao should not enable id FormControl', () => {
        const formGroup = service.createTermoDeAdesaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTermoDeAdesao should disable id FormControl', () => {
        const formGroup = service.createTermoDeAdesaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
