import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { PerfilAcessoService } from '../service/perfil-acesso.service';
import { IPerfilAcesso } from '../perfil-acesso.model';
import { PerfilAcessoFormService } from './perfil-acesso-form.service';

import { PerfilAcessoUpdateComponent } from './perfil-acesso-update.component';

describe('PerfilAcesso Management Update Component', () => {
  let comp: PerfilAcessoUpdateComponent;
  let fixture: ComponentFixture<PerfilAcessoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let perfilAcessoFormService: PerfilAcessoFormService;
  let perfilAcessoService: PerfilAcessoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PerfilAcessoUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PerfilAcessoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PerfilAcessoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    perfilAcessoFormService = TestBed.inject(PerfilAcessoFormService);
    perfilAcessoService = TestBed.inject(PerfilAcessoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const perfilAcesso: IPerfilAcesso = { id: 456 };

      activatedRoute.data = of({ perfilAcesso });
      comp.ngOnInit();

      expect(comp.perfilAcesso).toEqual(perfilAcesso);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPerfilAcesso>>();
      const perfilAcesso = { id: 123 };
      jest.spyOn(perfilAcessoFormService, 'getPerfilAcesso').mockReturnValue(perfilAcesso);
      jest.spyOn(perfilAcessoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ perfilAcesso });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: perfilAcesso }));
      saveSubject.complete();

      // THEN
      expect(perfilAcessoFormService.getPerfilAcesso).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(perfilAcessoService.update).toHaveBeenCalledWith(expect.objectContaining(perfilAcesso));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPerfilAcesso>>();
      const perfilAcesso = { id: 123 };
      jest.spyOn(perfilAcessoFormService, 'getPerfilAcesso').mockReturnValue({ id: null });
      jest.spyOn(perfilAcessoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ perfilAcesso: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: perfilAcesso }));
      saveSubject.complete();

      // THEN
      expect(perfilAcessoFormService.getPerfilAcesso).toHaveBeenCalled();
      expect(perfilAcessoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPerfilAcesso>>();
      const perfilAcesso = { id: 123 };
      jest.spyOn(perfilAcessoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ perfilAcesso });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(perfilAcessoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
