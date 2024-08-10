import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CidadeDetailComponent } from './cidade-detail.component';

describe('Cidade Management Detail Component', () => {
  let comp: CidadeDetailComponent;
  let fixture: ComponentFixture<CidadeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CidadeDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CidadeDetailComponent,
              resolve: { cidade: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CidadeDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CidadeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cidade on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CidadeDetailComponent);

      // THEN
      expect(instance.cidade()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
