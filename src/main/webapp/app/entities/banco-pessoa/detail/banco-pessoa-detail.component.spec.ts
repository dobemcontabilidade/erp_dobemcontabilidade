import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { BancoPessoaDetailComponent } from './banco-pessoa-detail.component';

describe('BancoPessoa Management Detail Component', () => {
  let comp: BancoPessoaDetailComponent;
  let fixture: ComponentFixture<BancoPessoaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BancoPessoaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: BancoPessoaDetailComponent,
              resolve: { bancoPessoa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(BancoPessoaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BancoPessoaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bancoPessoa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', BancoPessoaDetailComponent);

      // THEN
      expect(instance.bancoPessoa()).toEqual(expect.objectContaining({ id: 123 }));
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
