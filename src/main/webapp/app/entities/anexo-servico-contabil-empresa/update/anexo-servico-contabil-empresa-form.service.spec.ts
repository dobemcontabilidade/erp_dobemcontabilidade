import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../anexo-servico-contabil-empresa.test-samples';

import { AnexoServicoContabilEmpresaFormService } from './anexo-servico-contabil-empresa-form.service';

describe('AnexoServicoContabilEmpresa Form Service', () => {
  let service: AnexoServicoContabilEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnexoServicoContabilEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createAnexoServicoContabilEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAnexoServicoContabilEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            link: expect.any(Object),
            dataHoraUpload: expect.any(Object),
            anexoRequerido: expect.any(Object),
            servicoContabilAssinaturaEmpresa: expect.any(Object),
          }),
        );
      });

      it('passing IAnexoServicoContabilEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createAnexoServicoContabilEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            link: expect.any(Object),
            dataHoraUpload: expect.any(Object),
            anexoRequerido: expect.any(Object),
            servicoContabilAssinaturaEmpresa: expect.any(Object),
          }),
        );
      });
    });

    describe('getAnexoServicoContabilEmpresa', () => {
      it('should return NewAnexoServicoContabilEmpresa for default AnexoServicoContabilEmpresa initial value', () => {
        const formGroup = service.createAnexoServicoContabilEmpresaFormGroup(sampleWithNewData);

        const anexoServicoContabilEmpresa = service.getAnexoServicoContabilEmpresa(formGroup) as any;

        expect(anexoServicoContabilEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewAnexoServicoContabilEmpresa for empty AnexoServicoContabilEmpresa initial value', () => {
        const formGroup = service.createAnexoServicoContabilEmpresaFormGroup();

        const anexoServicoContabilEmpresa = service.getAnexoServicoContabilEmpresa(formGroup) as any;

        expect(anexoServicoContabilEmpresa).toMatchObject({});
      });

      it('should return IAnexoServicoContabilEmpresa', () => {
        const formGroup = service.createAnexoServicoContabilEmpresaFormGroup(sampleWithRequiredData);

        const anexoServicoContabilEmpresa = service.getAnexoServicoContabilEmpresa(formGroup) as any;

        expect(anexoServicoContabilEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAnexoServicoContabilEmpresa should not enable id FormControl', () => {
        const formGroup = service.createAnexoServicoContabilEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAnexoServicoContabilEmpresa should disable id FormControl', () => {
        const formGroup = service.createAnexoServicoContabilEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
