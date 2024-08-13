import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../feed-back-contador-para-usuario.test-samples';

import { FeedBackContadorParaUsuarioFormService } from './feed-back-contador-para-usuario-form.service';

describe('FeedBackContadorParaUsuario Form Service', () => {
  let service: FeedBackContadorParaUsuarioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FeedBackContadorParaUsuarioFormService);
  });

  describe('Service methods', () => {
    describe('createFeedBackContadorParaUsuarioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFeedBackContadorParaUsuarioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            comentario: expect.any(Object),
            pontuacao: expect.any(Object),
            criterioAvaliacaoAtor: expect.any(Object),
            usuarioEmpresa: expect.any(Object),
            contador: expect.any(Object),
            ordemServico: expect.any(Object),
          }),
        );
      });

      it('passing IFeedBackContadorParaUsuario should create a new form with FormGroup', () => {
        const formGroup = service.createFeedBackContadorParaUsuarioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            comentario: expect.any(Object),
            pontuacao: expect.any(Object),
            criterioAvaliacaoAtor: expect.any(Object),
            usuarioEmpresa: expect.any(Object),
            contador: expect.any(Object),
            ordemServico: expect.any(Object),
          }),
        );
      });
    });

    describe('getFeedBackContadorParaUsuario', () => {
      it('should return NewFeedBackContadorParaUsuario for default FeedBackContadorParaUsuario initial value', () => {
        const formGroup = service.createFeedBackContadorParaUsuarioFormGroup(sampleWithNewData);

        const feedBackContadorParaUsuario = service.getFeedBackContadorParaUsuario(formGroup) as any;

        expect(feedBackContadorParaUsuario).toMatchObject(sampleWithNewData);
      });

      it('should return NewFeedBackContadorParaUsuario for empty FeedBackContadorParaUsuario initial value', () => {
        const formGroup = service.createFeedBackContadorParaUsuarioFormGroup();

        const feedBackContadorParaUsuario = service.getFeedBackContadorParaUsuario(formGroup) as any;

        expect(feedBackContadorParaUsuario).toMatchObject({});
      });

      it('should return IFeedBackContadorParaUsuario', () => {
        const formGroup = service.createFeedBackContadorParaUsuarioFormGroup(sampleWithRequiredData);

        const feedBackContadorParaUsuario = service.getFeedBackContadorParaUsuario(formGroup) as any;

        expect(feedBackContadorParaUsuario).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFeedBackContadorParaUsuario should not enable id FormControl', () => {
        const formGroup = service.createFeedBackContadorParaUsuarioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFeedBackContadorParaUsuario should disable id FormControl', () => {
        const formGroup = service.createFeedBackContadorParaUsuarioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
