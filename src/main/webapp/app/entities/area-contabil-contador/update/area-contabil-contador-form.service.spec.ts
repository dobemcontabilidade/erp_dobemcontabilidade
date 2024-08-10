import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../area-contabil-contador.test-samples';

import { AreaContabilContadorFormService } from './area-contabil-contador-form.service';

describe('AreaContabilContador Form Service', () => {
  let service: AreaContabilContadorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AreaContabilContadorFormService);
  });

  describe('Service methods', () => {
    describe('createAreaContabilContadorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAreaContabilContadorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            percentualExperiencia: expect.any(Object),
            descricaoExperiencia: expect.any(Object),
            pontuacaoEntrevista: expect.any(Object),
            pontuacaoAvaliacao: expect.any(Object),
            areaContabil: expect.any(Object),
            contador: expect.any(Object),
          }),
        );
      });

      it('passing IAreaContabilContador should create a new form with FormGroup', () => {
        const formGroup = service.createAreaContabilContadorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            percentualExperiencia: expect.any(Object),
            descricaoExperiencia: expect.any(Object),
            pontuacaoEntrevista: expect.any(Object),
            pontuacaoAvaliacao: expect.any(Object),
            areaContabil: expect.any(Object),
            contador: expect.any(Object),
          }),
        );
      });
    });

    describe('getAreaContabilContador', () => {
      it('should return NewAreaContabilContador for default AreaContabilContador initial value', () => {
        const formGroup = service.createAreaContabilContadorFormGroup(sampleWithNewData);

        const areaContabilContador = service.getAreaContabilContador(formGroup) as any;

        expect(areaContabilContador).toMatchObject(sampleWithNewData);
      });

      it('should return NewAreaContabilContador for empty AreaContabilContador initial value', () => {
        const formGroup = service.createAreaContabilContadorFormGroup();

        const areaContabilContador = service.getAreaContabilContador(formGroup) as any;

        expect(areaContabilContador).toMatchObject({});
      });

      it('should return IAreaContabilContador', () => {
        const formGroup = service.createAreaContabilContadorFormGroup(sampleWithRequiredData);

        const areaContabilContador = service.getAreaContabilContador(formGroup) as any;

        expect(areaContabilContador).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAreaContabilContador should not enable id FormControl', () => {
        const formGroup = service.createAreaContabilContadorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAreaContabilContador should disable id FormControl', () => {
        const formGroup = service.createAreaContabilContadorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
