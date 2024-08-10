import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { PerfilRedeSocialService } from '../service/perfil-rede-social.service';
import { IPerfilRedeSocial } from '../perfil-rede-social.model';
import { PerfilRedeSocialFormService } from './perfil-rede-social-form.service';

import { PerfilRedeSocialUpdateComponent } from './perfil-rede-social-update.component';

describe('PerfilRedeSocial Management Update Component', () => {
  let comp: PerfilRedeSocialUpdateComponent;
  let fixture: ComponentFixture<PerfilRedeSocialUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let perfilRedeSocialFormService: PerfilRedeSocialFormService;
  let perfilRedeSocialService: PerfilRedeSocialService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PerfilRedeSocialUpdateComponent],
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
      .overrideTemplate(PerfilRedeSocialUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PerfilRedeSocialUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    perfilRedeSocialFormService = TestBed.inject(PerfilRedeSocialFormService);
    perfilRedeSocialService = TestBed.inject(PerfilRedeSocialService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const perfilRedeSocial: IPerfilRedeSocial = { id: 456 };

      activatedRoute.data = of({ perfilRedeSocial });
      comp.ngOnInit();

      expect(comp.perfilRedeSocial).toEqual(perfilRedeSocial);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPerfilRedeSocial>>();
      const perfilRedeSocial = { id: 123 };
      jest.spyOn(perfilRedeSocialFormService, 'getPerfilRedeSocial').mockReturnValue(perfilRedeSocial);
      jest.spyOn(perfilRedeSocialService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ perfilRedeSocial });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: perfilRedeSocial }));
      saveSubject.complete();

      // THEN
      expect(perfilRedeSocialFormService.getPerfilRedeSocial).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(perfilRedeSocialService.update).toHaveBeenCalledWith(expect.objectContaining(perfilRedeSocial));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPerfilRedeSocial>>();
      const perfilRedeSocial = { id: 123 };
      jest.spyOn(perfilRedeSocialFormService, 'getPerfilRedeSocial').mockReturnValue({ id: null });
      jest.spyOn(perfilRedeSocialService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ perfilRedeSocial: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: perfilRedeSocial }));
      saveSubject.complete();

      // THEN
      expect(perfilRedeSocialFormService.getPerfilRedeSocial).toHaveBeenCalled();
      expect(perfilRedeSocialService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPerfilRedeSocial>>();
      const perfilRedeSocial = { id: 123 };
      jest.spyOn(perfilRedeSocialService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ perfilRedeSocial });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(perfilRedeSocialService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
