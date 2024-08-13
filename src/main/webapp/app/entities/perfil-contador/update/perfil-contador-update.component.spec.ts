import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { PerfilContadorService } from '../service/perfil-contador.service';
import { IPerfilContador } from '../perfil-contador.model';
import { PerfilContadorFormService } from './perfil-contador-form.service';

import { PerfilContadorUpdateComponent } from './perfil-contador-update.component';

describe('PerfilContador Management Update Component', () => {
  let comp: PerfilContadorUpdateComponent;
  let fixture: ComponentFixture<PerfilContadorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let perfilContadorFormService: PerfilContadorFormService;
  let perfilContadorService: PerfilContadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PerfilContadorUpdateComponent],
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
      .overrideTemplate(PerfilContadorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PerfilContadorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    perfilContadorFormService = TestBed.inject(PerfilContadorFormService);
    perfilContadorService = TestBed.inject(PerfilContadorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const perfilContador: IPerfilContador = { id: 456 };

      activatedRoute.data = of({ perfilContador });
      comp.ngOnInit();

      expect(comp.perfilContador).toEqual(perfilContador);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPerfilContador>>();
      const perfilContador = { id: 123 };
      jest.spyOn(perfilContadorFormService, 'getPerfilContador').mockReturnValue(perfilContador);
      jest.spyOn(perfilContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ perfilContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: perfilContador }));
      saveSubject.complete();

      // THEN
      expect(perfilContadorFormService.getPerfilContador).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(perfilContadorService.update).toHaveBeenCalledWith(expect.objectContaining(perfilContador));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPerfilContador>>();
      const perfilContador = { id: 123 };
      jest.spyOn(perfilContadorFormService, 'getPerfilContador').mockReturnValue({ id: null });
      jest.spyOn(perfilContadorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ perfilContador: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: perfilContador }));
      saveSubject.complete();

      // THEN
      expect(perfilContadorFormService.getPerfilContador).toHaveBeenCalled();
      expect(perfilContadorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPerfilContador>>();
      const perfilContador = { id: 123 };
      jest.spyOn(perfilContadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ perfilContador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(perfilContadorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
